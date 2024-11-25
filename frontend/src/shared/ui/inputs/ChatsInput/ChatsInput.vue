<template>
  <div class="relative">
    <input
      type="text"
      @focus="onFocus"
      @blur="onBlur"
      v-model="modelValue"
      placeholder="Search..."
      class="w-full pl-5 pr-12 text-white py-3 transition-all bg-black-bg outline-none rounded-md focus:outline-none active:outline-none placeholder:text-input-placeholder placeholder:text-sm"
    />
    <div class="absolute top-1/2 right-3 -translate-y-1/2 -translate-x-1/2 w-5 h-5">
      <SearchIcon :isActive="isActive" :width="16" :height="16"></SearchIcon>
    </div>
  </div>
</template>

<script setup lang="ts">
import { SearchIcon } from '@/shared/ui/icons/inputIcons'
import { ref } from 'vue'

const isActive = ref(false)

const modelValue = ref()

const onFocus = () => {
  isActive.value = true
}
const onBlur = () => {
  isActive.value = false
}
</script>

<style scoped></style>
