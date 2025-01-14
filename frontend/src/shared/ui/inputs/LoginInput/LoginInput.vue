<template>
  <div>
    <div class="relative z-10">
      <div class="absolute top-1/2 left-1 -translate-y-1/2 -z-10 w-8 h-8">
        <slot></slot>
      </div>
      <input
        :type="inputType"
        v-model="modelValue"
        :placeholder="placeholder"
        class="w-full pl-9 pr-2 py-2 transition-all text-main bg-transparent border-2px border-accent-main rounded-md focus:outline-accent-blue active:outline-accent-blue placeholder:text-grey-main"
      />
    </div>
    <transition name="fade">
      <div class="text-red-600" v-if="err">Error</div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
interface Props {
  inputType?: string
  placeholder?: string
}

const err = ref(false)

const props = withDefaults(defineProps<Props>(), {
  inputType: 'text'
})

const modelValue = defineModel()
</script>

<style scoped>
.error {
  border: 2px solid #dc2626;
}
</style>
