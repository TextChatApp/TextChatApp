<template>
  <svg
    xmlns="http://www.w3.org/2000/svg"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    viewBox="0,0,256,256"
    width="30"
    height="30"
    fill-rule="nonzero"
    class="transition-all"
  >
    <g
      :fill="!isActive ? '#5F6388' : '#fff'"
      fill-rule="nonzero"
      stroke="none"
      stroke-width="1"
      stroke-linecap="butt"
      stroke-linejoin="miter"
      stroke-miterlimit="10"
      stroke-dasharray=""
      stroke-dashoffset="0"
      font-family="none"
      font-weight="none"
      font-size="none"
      text-anchor="none"
      style="mix-blend-mode: normal"
    >
      <g transform="scale(4,4)">
        <path
          d="M32,10c-12.15,0 -22,9.85 -22,22c0,12.15 9.85,22 22,22c12.15,0 22,-9.85 22,-22c0,-12.15 -9.85,-22 -22,-22zM32,14c9.941,0 18,8.059 18,18c0,9.941 -8.059,18 -18,18c-9.941,0 -18,-8.059 -18,-18c0,-9.941 8.059,-18 18,-18zM32,22c-1.105,0 -2,0.896 -2,2v6h-6c-1.104,0 -2,0.895 -2,2c0,1.105 0.896,2 2,2h6v6c0,1.104 0.895,2 2,2c1.105,0 2,-0.896 2,-2v-6h6c1.104,0 2,-0.895 2,-2c0,-1.105 -0.896,-2 -2,-2h-6v-6c0,-1.104 -0.895,-2 -2,-2z"
        ></path>
      </g>
    </g>
  </svg>
</template>

<script setup lang="ts">
interface Props {
  isActive?: boolean
}

const props = defineProps<Props>()
</script>

<style scoped>
svg:hover {
  transform: scale(1.2);
}
</style>
