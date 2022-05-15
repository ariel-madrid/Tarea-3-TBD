<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded w-80 ml-2 border border-gray-200 place-content-center">
     

        <div class="mt-10 flex flex-col justify-center">
            <p>{{ message }}</p>

            <label for="nDogs">Seleccione la cantidad N de perros a obtener:</label>
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
import axios from "axios";

export default {
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
            this.dogId = 1;
            this.n = 2;
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
    }
}
</script>