<template>
  <div>
    <button
      class="w-fit px-10 py-1 text-center bg-accent-main rounded-xl text-white transition-all hover:opacity-90 focus:outline-accent-blue active:translate-y-1"
      :class="{ 'bg-green-500': isCopied }"
      @click="copyText()"
    >
      {{ isCopied ? placeholderCopied : placeholderToCopy }}
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

interface Props {
  textToCopy: string | undefined
  placeholderToCopy?: string
  placeholderCopied?: string
}

const props = defineProps<Props>()

const isCopied = ref(false)

const copyText = async () => {
  try {
    if (props.textToCopy) {
      await navigator.clipboard.writeText(props.textToCopy)
      isCopied.value = true
      setTimeout(() => (isCopied.value = false), 2000)
    }
  } catch (err) {
    console.log(err)
  }
}
</script>

<style></style>
