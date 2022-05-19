<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded ml-2 border border-gray-200 place-content-center">
        
        <div class="mt-10 flex flex-col justify-center">
            <p>{{ message }}</p>
            <p class> El perro seleccionado es {{ selectedPoint.name }} con id {{ selectedPoint.id }}</p>
            <div class = "mb-3 w-full" id="mapid4"></div>
            <label for="nDogs">Seleccione el radio "R" en metros para obtener los perros mas cercanos:</label> 
            <input class="border-solid border-2" 
                   v-model="r"
                   id="nDogs" type="number" value="1"/>
            <p>{{ this.r == null || this.r == ""
                  ? "Seleccione R"
                  : `R: ${this.r} metros` 
               }}
            </p> 
            <p class = "mt-5 mb-0"> La cantidad de perros encontrados de acuerdo al radio seleccionado es: {{contador}}</p>
            <button class="mt-5 ml-5 mr-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600" 
                    @click="getRNearDogs()">
                    Obtener resultados
            </button>
        </div>
        
        <button @click="emit" 
                class="mt-5 ml-5 mr-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600">
                Volver
        </button>

    </div>
</template>

<script>

import "leaflet/dist/leaflet"; 
import "leaflet/dist/leaflet.css";
var icon = require("leaflet/dist/images/marker-icon.png"); 
var LeafIcon = L.Icon.extend({
    options: { iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [-3, -41] },
});
var myIcon = new LeafIcon({ iconUrl: icon });

import axios from "axios";

export default {
    props: {
        selectedPoint: { type: Object },
    },
    data() {
        return {
            dogId: null,
            r: null,
            message: "",
            value: false,
            perrosR: [], 
            marcadores: [],
            contador: 0
        };
    },
    methods: {
        async getRNearDogs(){
        
            this.marcadores.forEach(marcador => {
                    this.mymap.removeLayer(marcador);
            });

            this.selectedPoint.radio = this.r;
            console.log(this.selectedPoint);
            try {
                this.perrosR = await axios.post("http://localhost:8080/dogs/rneardogs", this.selectedPoint)
                this.perrosR = this.perrosR.data
                this.contador = this.perrosR.length
                //console.log(this.contador)
                //console.log(perrosR)
            } catch (error) {
                console.log('error', error);
                this.message = 'Ocurrió un error'
            } 
            
            if(this.contador !== 0){

                //Se añaden los marcadores
                this.perrosR.forEach(perro => {
                    let p = [perro.latitude, perro.longitude]
                    let marker = L.marker(p, { icon: myIcon}) //se define el ícono del marcador
                    .bindPopup(perro.name)

                    marker.addTo(this.mymap);

                    this.marcadores.push(marker);
                });
            }
            else{
                this.marcadores.forEach(marcador => {
                    this.mymap.removeLayer(marcador);
                });
                alert("No se encontraron perros en el radio seleccionado");
            }

            
            
        },
        emit()
        {
            this.$emit('close', true)
        }
    },
    mounted() {

        if(this.selectedPoint.id === undefined){
            alert("Debe seleccionar un perro");
            this.emit();
        } 
        
        this.mymap = L.map("mapid4").setView([-33.456, -70.648], 7);
        //Se definen los mapas de bits de OSM
        L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
        attribution:
            '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        maxZoom: 15,
        }).addTo(this.mymap);
    },  
}
</script>

<style> 
    #mapid4 {
        width: 550px;
        aspect-ratio: 16/9;
    }
</style>