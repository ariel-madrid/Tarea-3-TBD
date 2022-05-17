<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded w-80 ml-2 border border-gray-200 h-64 place-content-center">
        
        <!-- This example requires Tailwind CSS v2.0+ -->
        <div class="relative inline-block text-left">
            <div>
                <button @click="showCombobox" type="button" class="inline-flex justify-center w-full rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500" id="menu-button">
                Seleccionar Región
                <svg class="-mr-1 ml-2 h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                </svg>
                </button>
            </div>
            <div v-if="combobox" class="origin-top-right absolute right-7 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none" tabindex="-1">
                <div class="py-1" role="none">
                <button @click="getRegions(region)" class="w-full text-gray-700 block px-4 py-2 text-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500" 
                        v-for="region in regions" :key="region.gid">
                        {{ region.nom_reg }}
                </button>
                </div>
            </div>
        </div>
        <div id="mapid5"></div>
        <div class="mt-10 flex justify-center">
            <p>{{ `Hay ${nPerros} perros en ${regionSeleccionada}` }}</p>
        </div>

        <button @click="emit" class="bg-blue-500 rounded mt-10 w-full text-gray-700 block px-4 py-2 text-sm hover:bg-blue-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">Volver</button>

    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            regionSeleccionada: "(región no seleccionada)",
            nPerros: 0,
            combobox: false,
            regions: [],
            value: false
        };
    },
    methods: {
        showCombobox()
        {
            if (this.combobox == true)
            {
                this.combobox = false
            }
            else 
            {
                this.combobox=true
            }
        },
        emit()
        {
            this.$emit('close', true)
        },
        getRegions(region){
            alert(region.nom_reg)
        }
    },
    mounted: async function(){
            try{
                let response = await this.$axios.get("http://localhost:8080/regions");
                this.regions = response.data
                console.log(this.regions)

            }
            catch(e){
                console.log(e)
            }
            this.mymap = L.map("mapid5").setView([-33.456, -70.648], 7);
            //Se definen los mapas de bits de OSM
            L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
            attribution:
                '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 10,
            }).addTo(this.mymap);
        }
}
</script>
<style>
    #mapid5 {
        width: 400px;
        aspect-ratio: 16/9;
    }
</style>
