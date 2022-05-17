<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded ml-2 border border-gray-200 place-content-center">

        <div class="mt-10 flex flex-col justify-center">
            <p>{{ message }}</p>

            <div id="mapid4"></div>
            <p>El perro seleccionado es {{ selectedPoint.name }} con id {{ selectedPoint.id }}</p>
            <label for="nDogs">Seleccione el radio "R" en metros para obtener los perros mas cercanos:</label> 
            <input class="border-solid border-2" 
                   v-model="r"
                   id="nDogs" type="number" value="1"/>
            <p>{{ this.r == null || this.r == ""
                  ? "Seleccione R"
                  : `R: ${this.r} metros` 
               }}
            </p>
            <button class="bg-blue-500 rounded mt-10 w-full text-gray-700 py-2" 
                    @click="getRNearDogs()">
                    Get!
            </button>
        </div>
        
        <button @click="emit" 
                class="bg-blue-500 rounded mt-10 w-full text-gray-700 block px-4 py-2 text-sm hover:bg-blue-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
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
            value: false
        };
    },
    methods: {
        async getRNearDogs(){
            this.selectedPoint.radio = this.r;
            console.log(this.selectedPoint);
            try {
                let response = await axios.post("http://localhost:8080/dogs/rneardogs", this.selectedPoint)
                response = response.data
                console.log(response)
            } catch (error) {
                console.log('error', error);
                this.message = 'Ocurrió un error'
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
        maxZoom: 10,
        }).addTo(this.mymap);
    },  
}
</script>

<style> 
    #mapid4 {
        width: 400px;
        aspect-ratio: 16/9;
    }
</style>