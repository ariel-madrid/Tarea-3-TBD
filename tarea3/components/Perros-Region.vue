<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded w-80 ml-2 border border-gray-200 w-max place-content-center mb-40">
        
        <!-- This example requires Tailwind CSS v2.0+ -->
        <div class="relative inline-block text-left">
            <div class = "mb-10" id="mapid5"></div>
            <p class="mb-5">La cantidad de perros en la region seleccionada es de: {{contador}}</p>
            <div>
                <button @click="showCombobox" type="button" class="inline-flex justify-center w-full rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500" id="menu-button">
                Seleccionar Región
                <svg class="-mr-1 ml-2 h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                </svg>
                </button>
            </div>
            <div v-if="combobox" class="origin-top-right right-14 mt-2 w-64 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none" tabindex="-1">
                <div class="py-1" role="none">
                <button @click="filtrarRegion(region)" class="w-80 text-left w-full text-gray-700 block px-4 py-2 text-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500" 
                        v-for="region in regions" :key="region.gid">
                        {{ region.nom_reg }}
                </button>
                </div>
            </div>
        </div>
        

        <button @click="emit" class="bg-blue-500 rounded mt-10 w-full text-gray-700 block px-4 py-2 text-sm hover:bg-blue-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">Volver</button>

    </div>
</template>

<script>
import axios from "axios";
var icon = require("leaflet/dist/images/marker-icon.png"); 
var LeafIcon = L.Icon.extend({
  options: { iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [-3, -41] },
});
var myIcon = new LeafIcon({ iconUrl: icon });

export default {
    data() {
        return {
            regionSeleccionada: "(región no seleccionada)",
            nPerros: 0,
            combobox: false,
            regions: [],
            value: false,
            dogs:[],
            perroFiltrado: [],
            perrosMatch: [],
            contador: 0,
            marcadores: []
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
        async filtrarRegion(region){
            this.contador = 0
            this.combobox = false


            let perros = await this.$axios.get("http://localhost:8080/dogs")
            perros = perros.data
            
            for (let i = 0; i < perros.length; i++)
            {
                perros[i].codRegi = region.cod_regi 

                this.perroFiltrado = await this.$axios.post("http://localhost:8080/intersection",perros[i]);
                this.perroFiltrado = this.perroFiltrado.data
                //console.log(this.perroFiltrado)

                //Guardar perros en un arreglo, los que hacen match
                if(this.perroFiltrado[0] != undefined){
                    this.perrosMatch.push(this.perroFiltrado[0])
                    this.contador = this.contador + 1;
                }
            }
            
            if (this.contador === 0)
            {
                alert("No existen perros en la region "+region.nom_reg)
                this.marcadores.forEach(marcador => {
                    this.mymap.removeLayer(marcador)
                })
            }else 
            {
                //Que se añadan los marcadores.
                this.perrosMatch.forEach(perro => {
                    let p = [perro.latitude, perro.longitude]
                    let marker = L.marker(p, { icon: myIcon }) //se define el ícono del marcador
                    .bindPopup(perro.name)

                    marker.addTo(this.mymap);
                    this.marcadores.push(marker)
                });
                this.mymap.setView([this.perrosMatch[0].latitude, this.perrosMatch[0].longitude], 8);
            }
        }
    },
    mounted: async function(){
            try{
                let tmp = 0;
                let response = await this.$axios.get("http://localhost:8080/regions");
                this.regions = response.data
                this.regions = this.regions.filter(region => region.nom_reg != 'Zona sin demarcar')
                this.regions = this.regions.filter(region => region.nom_reg != null)
            }
            catch(e){
                console.log(e)
            }

            this.mymap = L.map("mapid5").setView([-33.456, -70.648], 7);
            //Se definen los mapas de bits de OSM
            L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
            attribution:
                '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 15,
            }).addTo(this.mymap);
        }
}
</script>
