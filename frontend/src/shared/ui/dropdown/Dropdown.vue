<template>
  <div v-if="isOpen" class="dropdown-menu overflow-y-auto">
    <div class="">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  isOpen: Boolean
})
</script>

<style scoped>
.dropdown-menu {
  position: absolute;
  top: 50px;
  left: 0px;
  transform: translateX(calc(-100% + 32px));
  background-color: #212229;
  border: 1px solid #5051f9;
  border-radius: 4px;
  width: max-content;
  max-height: 400px;
  overflow-y: auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  padding: 10px;
  z-index: 10;
}
</style>
