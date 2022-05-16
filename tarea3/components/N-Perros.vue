<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded ml-2 border border-gray-200 place-content-center">
     

        <div class="mt-10 flex flex-col justify-center">
            <p>{{ message }}</p>

            <div id="mapid3"></div>
            <p>El perro seleccionado es {{ selectedPoint.name }} con id {{ selectedPoint.id }}</p>
            <label for="nDogs">Seleccione la cantidad "N" de perros más cercanos a obtener:</label>
            <input class="border-solid border-2" 
                   v-model="n"
                   id="nDogs" type="number" value="1"/>
            <p>{{ this.n == null || this.n == ""
                  ? "Seleccione N"
                  : `N: ${this.n} perros` 
               }}
            </p>
            <button class="bg-blue-500 rounded mt-10 w-full text-gray-700 py-2" 
                    @click="getNNearDogs()">
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
            n: null,
            message: "",
            value: false
        };
    },
    methods: {
        async getNNearDogs(){
            this.dogId = this.selectedPoint.id;
            try {
                let response = await axios.get("http://localhost:8080/dogs/nneardogs", {
                    params: {dogId: this.dogId, n: this.n}
                });

                } catch (error) {
                console.log('error', error); 
                this.message = 'Ocurrió un error'
            }
        },
        emit(){
            
            this.$emit('close', true)
        }
    },
    mounted() {
        this.mymap = L.map("mapid3").setView([-33.456, -70.648], 7);
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
    #mapid3 {
        width: 400px;
        aspect-ratio: 16/9;
    }
</style>