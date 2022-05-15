<!-- Please remove this file from your project -->
<template>
  <div class="bg-red-400 fixed overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center z-50">
    <div v-if="toggleModal==3">
      <PrincipalWithOpacity @toggleModal="dataFromChild"></PrincipalWithOpacity>
    </div>
    <div v-else class="opacity-25">
      <PrincipalWithOpacity></PrincipalWithOpacity>
    </div>

    <div v-if="toggleModal==0" id="defaultModal" class="fixed overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center z-50">
      <PerrosRegion @close="cerrar"/>
    </div>
    <div v-if="toggleModal==1" id="defaultModal" class="fixed overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center z-50">
      <NPerros @close="cerrar"/>
    </div>
    <div v-if="toggleModal==2" id="defaultModal" class="fixed overflow-x-hidden overflow-y-auto inset-0 flex justify-center items-center z-50">
      <PerrosRadio @close="cerrar"/>
    </div>

  </div>
</template>

<script>
import PerrosRegion from "./Perros-Region.vue";
import NPerros from "./N-Perros.vue";
import PerrosRadio from "./Perros-Radio.vue";
import PrincipalWithOpacity from "./Principal-with-opacity.vue";
export default {
    data() {
        return {
            toggleModal: 3,
            close: false
        };
    },
    methods: {
        dataFromChild(value)
        {
            this.toggleModal = value
        },
        cerrar()
        {
          this.toggleModal = 3
        }
    },
    components: { PerrosRegion, NPerros, PerrosRadio, PrincipalWithOpacity }
}
</script>
