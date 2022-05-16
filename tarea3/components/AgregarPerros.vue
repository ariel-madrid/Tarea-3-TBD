<template>
    <div class="Contenedora">
        <h1>Agregar Perro</h1>
        <form>
            <div >
                <label for="nombrePerro">Ingrese el nombre del perro</label>
                <input type="text" id="namePerro" v-model="nuevoPerro.name">
            </div>
            <div>
                <label for="longPerro">Ingrese la categoria de la pelicula</label>
                <input type="number" id="longitudePerro" v-model="nuevoPerro.longitude">
                
            </div>
            <div>
                <label for="latiPerro">Ingrese la duracion de la pelicula</label>
                <input type="number" id="latitudePerro" v-model="nuevoPerro.latitude">
            </div>
            <div>
            <button type="button" id="agregar" @click="agregarPerro" class="main">Agregar perro</button>
            </div>
        </form>

    </div>
</template>

<script>

export default ({
    
    data(){
        return {
            url: '',
            nuevoPerro: {},
            temp: {},
        }
    },

    methods: {


    async agregarPerro() {
      try {
        let response = await this.$axios.get("http://localhost:8080/dog");
        this.temp = response.data;
        var flag = 0;
        for (var i = 0; i < this.temp.length; i++) {
          if (this.temp[i].name === this.nuevoPerro.name) {
            alert("Ya existe un perro con este nombre en la base de datos");
            i = this.temp.length;
            flag = 1;
          }
        }
        if(this.nuevoPerro.longitude == null ){
            alert("Debe ingresar una longitud");
        }
        else if(this.nuevoPerro.categoria == null){
            alert("Debe ingresar una latitud");
        }
        else{
        if (flag == 0) {
           
          try {
            await this.$axios
              .post("http://localhost:8080/nuevodog", this.nuevoPerro)
              .then((res) => res.data)
              .catch((res) => res);

          } catch (error) {
            console.log("error", error);
          }
        }
        
        }
        
      } catch (error) {
        console.log("error", error);
      }
    },
  },
})
</script>

<style>
    
</style>
