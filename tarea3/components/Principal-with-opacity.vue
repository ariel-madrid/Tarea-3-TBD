<template>
    <div class="bg-white shadow-sm p-5 rounded max-w-sm ml-2 border border-gray-200">
      <h3>Seleccione una opcion</h3>
      <div>
        <button @click="passData(0)" class="mt-5 ml-5 mr-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600" type="button" data-modal-toggle="defaultModal">
          Mostrar todos los perros de una region especifica
        </button>
        <button @click="passData(1)" class="mt-5 ml-5 mr-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600" type="button" data-modal-toggle="defaultModal">
          Mostrar N perros mas cercanos a un perro
        </button>
        <button @click="passData(2)" class="mt-5 ml-5 mr-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600" type="button" data-modal-toggle="defaultModal">
          Mostrar perros mas cercanos a un perro en radio R
        </button>
      </div>
    </div>
</template>

<script>
import Vue from 'vue'
export default {
    data() {
        return {
            toggleModal: null
        };
    },
    methods:
    {
        passData(value) 
        {
            this.toggleModal = value
            this.$emit('toggleModal', this.toggleModal)
        }
    }
}
</script>