<template>
    <div class="grid grid-cols-1 bg-white shadow-sm p-5 rounded w-80 ml-2 border border-gray-200 h-64 place-content-center">
     

        <div class="mt-10 flex justify-center">
            Aqui va el mapa
        </div>

        <button @click="emit" class="bg-blue-500 rounded mt-10 w-full text-gray-700 block px-4 py-2 text-sm hover:bg-blue-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-indigo-500">Volver</button>

    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            value: false
        };
    },
    methods: {
        emit()
        {
            this.$emit('close', true)
        }
    }
}
</script>