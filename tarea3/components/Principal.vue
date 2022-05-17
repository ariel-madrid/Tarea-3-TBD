<!-- Please remove this file from your project -->
<template>
  <div class="bg-red-400 w-screen h-screen flex flex-col justify-center items-center">
    
    <div class="overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center">
      <div v-if="toggleModal==3">
        <PrincipalWithOpacity @toggleModal="dataFromChild"></PrincipalWithOpacity>
      </div>
      <div v-else class="opacity-25">
        <PrincipalWithOpacity></PrincipalWithOpacity>
      </div>

      <div v-if="toggleModal==0" id="defaultModal" class="fixed overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center z-20">
        <PerrosRegion @close="cerrar"/>
      </div>
      <div v-if="toggleModal==1" id="defaultModal" class="fixed overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center z-20">
        <NPerros :selectedPoint="selectedPoint" @close="cerrar"/>
      </div>
      <div v-if="toggleModal==2" id="defaultModal" class="fixed overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center z-20">
        <PerrosRadio :selectedPoint="selectedPoint" @close="cerrar"/>
      </div>
    </div>
    
    <div v-bind:style=" toggleModal != 3 ? 'opacity: 25%;' : 'opacity: 1;' ">
      <div id="principalmap" class="mt-10">
      </div>

      <div class="m-3">
        {{ point }} 
        <input  class="pl-5 px-5 py-2.5 " type="text" v-model="name" placeholder="Nombre perro" />
        <button class="mt-5 ml-5 mr-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600" type="button" @click="crearPerro()">Crear</button>
      </div>
    </div>
    
  </div>
</template>

<script>

// Importaciones para usar leaflet
import "leaflet/dist/leaflet"; 
import "leaflet/dist/leaflet.css";
var icon = require("leaflet/dist/images/marker-icon.png"); 
var LeafIcon = L.Icon.extend({
  options: { iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [-3, -41] },
});
var myIcon = new LeafIcon({ iconUrl: icon });

// Obtener componentes
import PerrosRegion from "./Perros-Region.vue";
import NPerros from "./N-Perros.vue";
import PerrosRadio from "./Perros-Radio.vue";
import PrincipalWithOpacity from "./Principal-with-opacity.vue";

export default {
    data() {
        return {
            toggleModal: 3,
            close: false,
            latitude: null, // Datos para el nuevo punto
            longitude: null,
            points: [], // Arreglo de puntos en el lado del cliente
            //points2: [], // Arreglo de puntos para los perros 
            name: "",
            selectedPoint: {}, // Punto que se pasa a componente NPerros,
            puntos: [],
            cod_regi: null
        };
    },
    methods: {
      async crearPerro(){
        //Crear un nuevo punto
        // this.message = "";

        // realizar llamada para crear un nuevo perro

        let newPoint = { 
          name: this.name,
          latitude: this.latitude,
          longitude: this.longitude,
          cod_regi: this.cod_regi
        };
        try {
          let response = await this.$axios.get("http://localhost:8080/dogs");
          this.temp = response.data;
          var flag = 0;
          var count = 0;
          for (var i = 0; i < this.temp.length; i++) {
            if (this.temp[i].name === newPoint.name) {
              alert("Ya existe un perro con este nombre en la base de datos");
              i = this.temp.length;
              flag = 1;
            }
            count++;
          }
          if (flag == 0) {
            try {
              newPoint.id = count+1;
<<<<<<< Updated upstream
              alert(newPoint.latitude+" "+newPoint.longitude)
=======
              newPoint.lat = newPoint.latitude;
              newPoint.long = newPoint.longitude;
>>>>>>> Stashed changes
              await this.$axios
                .post("http://localhost:8080/nuevodog", newPoint)
                .then((res) => res.data)
                .catch((res) => res);

            } catch (error) {
              console.log("error", error);
            }
          }
        } catch (error) {
          console.log("error", error);
        }

        this.points.push(newPoint);

        let p = [newPoint.latitude, newPoint.longitude];
        let marker = L.marker(p, { icon: myIcon }) //se define el ícono del marcador
          .bindPopup(newPoint.name)
          .on('click', (e) => this.recuperarPunto(e)); //Se agrega un popup con el nombre

        marker.addTo(this.mymap);

        // this.message = `${this.name} fue creado con éxito`;
        this.name = "";

        alert("Creando perro...")
        location.reload()
      },
      
      async getPoints(map){
        try {
          //se llama el servicio 
          let response = await this.$axios.get("http://localhost:8080/dogs");
          let dataPoints = response.data;
          this.puntos = dataPoints
          //Se itera por los puntos
          dataPoints.forEach(point => {
 
            //Se crea un marcador por cada punto
            let p =[point.latitude, point.longitude]
            let marker = L.marker(p, {icon:myIcon}) //se define el ícono del marcador
            .bindPopup(point.name) //Se agrega un popup con el nombre
            .on('click', (e) => this.recuperarPunto(e));
            //Se agrega a la lista
            this.points.push(marker);
          });

          //Los puntos de la lista se agregan al mapa
          this.points.forEach(p=>{
            p.addTo(map)
          })
        } catch (error) {
          console.log('error', error); 
        }
      },

      dataFromChild(value){
        this.toggleModal = value
      },
      cerrar(){
        this.toggleModal = 3
      },
      recuperarPunto(e){
        let selectedLatlng = e.latlng;

        let lat =  selectedLatlng.lat;
        let long = selectedLatlng.lng;

        for(let p of this.puntos){
          
          if( p.latitude == lat && p.longitude == long ){
            this.selectedPoint = p;
            return;
          }
        }
      }
    },
    computed: {
      point(){
        // función computada para representar el punto seleccionado
        if (this.latitude && this.longitude) {
          let lat = this.latitude.toFixed(3);
          let lon = this.longitude.toFixed(3);
          return `(${lat}, ${lon})`;
        } else {
          return "";
        }
      },
    },
    mounted(){
      let _this = this;
     
      this.mymap = L.map("principalmap").setView([-33.456, -70.648], 7);

      // Se definen los mapas de bits de OSM
      L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
        attribution:
            '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        maxZoom: 15,
      }).addTo(this.mymap);

      this.mymap.on("click", function (e) {
        _this.latitude = e.latlng.lat;
        _this.longitude = e.latlng.lng;
        console.log(e)
      });

      //Se agregan los puntos mediante llamada al servicio
      this.getPoints(this.mymap); 

    },
    
    components: { PerrosRegion, NPerros, PerrosRadio, PrincipalWithOpacity },
}
</script>

<style>
    #principalmap {
        z-index: 0;
        width: 450px;
        aspect-ratio: 16/9;
    }
</style>