<template>
  <input
    :type="inputType"
    v-model="modelValue"
    :placeholder="placeholder"
    :class="[{ error: error }]"
    class="w-full pl-2 pr-2 py-2 transition-all text-white bg-transparent border-2px border-accent-main rounded-md focus:outline-accent-blue active:outline-accent-blue placeholder:text-grey-main"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'
interface Props {
  inputType?: string
  placeholder?: string
  error?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  inputType: 'text'
})

const modelValue = defineModel()
</script>

<style scoped>
.error {
  border: 2px solid #dc2626;
  outline: none;
}
</style>
