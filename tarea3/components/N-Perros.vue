<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded ml-2 border border-gray-200 place-content-center">
     

        <div class="mt-10 flex flex-col justify-center">
            <p class="border-solid border-b-4 p-4 border-red-500 mb-2" 
               v-if="this.message">Informaciones: {{message}}</p>

            <p>> El perro seleccionado es {{ selectedPoint.name }} con id {{ selectedPoint.id }}</p>
            <label for="nDogs">Seleccione la cantidad "N" de perros más cercanos a obtener:</label>
            <input class="border-solid border-2" 
                   v-model="n"
                   id="nDogs" type="number" value="1"/>
            <p>{{ this.n == null || this.n == ""
                  ? "Seleccione N"
                  : `N: ${this.n} perros` 
               }}
            </p>
            <button class="bg-blue-500 rounded mt-2 w-full text-gray-700 py-2" 
                    @click="getNNearDogs()">
                    Obtener resultados
            </button>

            <div v-if="this.results.length != 0" class="mt-6 mb-6">
                <h1>Los resultados obtenidos son:</h1>
                <table class="table auto">
                    <thead class="border-b-2 border-gray-500" >
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Distancia con el perro seleccionado [metros]</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="border-b-2 border-gray-500" v-for="d in this.results">
                            <th class="px-6" v-text="d.id"></th>
                            <th class="px-6" v-text="d.name"></th>
                            <th v-text="parseFloat(d.distance).toFixed(3)"></th>
                        </tr>
                    </tbody>
                </table>
            </div>

            
            <button @click="emit" 
                    class="bg-blue-500 rounded mt-2 w-full text-gray-700 block px-4 py-2 text-sm hover:bg-blue-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">
                    Volver
            </button>

        </div>
        
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
            value: false,
            results: [],
        };
    },
    methods: {
        async getNNearDogs(){
            this.message = "";
            let n = this.n;

            // Validaciones
            if( n === null ){
                alert("Seleccione N");
                return;
            }
            if( n < 0 ){
                alert("No puede elegir una cantidad N negativa");
                return;
            }


            this.dogId = this.selectedPoint.id;
            try {
                let response = await axios.get("http://localhost:8080/dogs/nneardogs", {
                    params: {dogId: this.dogId, n: n}
                });

                let results = response.data
                if( results.length == 0 ){
                    this.message = "Sin resultados";
                    return;
                }

                this.results = results.map((dog) => {
                    return { id: dog.id, 
                        name: dog.name, 
                        distance: dog.distance
                    }
                });

                if( n > this.results.length ){
                    this.message = "Mostrando todos los perros, ya que no hay más perros."
                }

            } catch (error) {
                console.log('error', error); 
                this.message = 'Ocurrió un error'
            }
        },
        emit(){
            this.$emit('close', true);
        }
    },
    mounted() {

        if(this.selectedPoint.id === undefined){
            alert("Debe seleccionar un perro");
            this.emit();
        }

    },
}
</script>
