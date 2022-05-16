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
        <PerrosRadio @close="cerrar"/>
      </div>
    </div>
    
    <div v-bind:style=" toggleModal != 3 ? 'opacity: 25%;' : 'opacity: 1;' ">
      <div id="principalmap" class="mt-10">
      </div>

      <div>
        {{ point }} 
        <input type="text" v-model="name" placeholder="nombre" />
        <button type="button" @click="crearPerro()">Crear</button>
        <p>{{ selectedPoint }}</p>
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
            name: "",
            selectedPoint: {}, // Punto que se pasa a componente NPerros
        };
    },
    methods: {
      crearPerro(){
      //Crear un nuevo punto
      // this.message = "";

      // realizar llamada para crear un nuevo perro

      let newPoint = { 
        name: this.name,
        latitude: this.latitude,
        longitude: this.longitude,
      };

      this.points.push(newPoint);

      let p = [newPoint.latitude, newPoint.longitude];
      let marker = L.marker(p, { icon: myIcon }) //se define el ícono del marcador
          .bindPopup(newPoint.name)
          .on('click', (e) => this.recuperarPunto(e)); //Se agrega un popup con el nombre

      marker.addTo(this.mymap);


      // this.message = `${this.name} fue creado con éxito`;
      this.name = "";


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

        for(let p of this.points){
          console.log(p.id)
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

      // Realizar llamada para obtener todos los perros aquí
      // let dogs = llamar servicio
      // Añadir un marcador por cada perro
      // dogs.forEach((dog) => {
      //   let p = [newPoint.latitude, newPoint.longitude];
      //   let marker = L.marker(p, { UID: newPoint.id, icon: myIcon }) //se define el ícono del marcador
      //     .bindPopup(newPoint.name)
      //     .on('click', (e) => {
              // esta funcion sirve para los N-perros para seleccionar un perro
      //       this.selectedLatlng = e.latlng;
      //       console.log(this.selectedLatlng);
            

      //     }
      //     ); //Se agrega un popup con el nombre

      //   marker.addTo(this.mymap);
      // });
      

      this.mymap = L.map("principalmap").setView([-33.456, -70.648], 7);
      // Se definen los mapas de bits de OSM
      L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
        attribution:
            '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        maxZoom: 10,
      }).addTo(this.mymap);

      this.mymap.on("click", function (e) {
        _this.latitude = e.latlng.lat;
        _this.longitude = e.latlng.lng;
      });

      // añadir regiones
      // hacer llamada para obtenerlas
      // L.geoJSON(this.myjson).addTo(this.mymap);

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