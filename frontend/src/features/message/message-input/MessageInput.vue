<template>
  <div class="w-full relative">
    <input
      @keydown.enter="emits('send-message')"
      type="text"
      placeholder="Your message"
      v-model="modelValue"
      class="w-full pl-5 pr-12 text-white py-3 transition-all bg-main outline-none rounded-xl focus:outline-none active:outline-none placeholder:text-input-placeholder placeholder:text-sm"
    />
    <div class="absolute top-1 translate-y-1/2 right-5 flex gap-4">
      <SmileIcon @click="togglePicker()" :isActive="pickerOpen"></SmileIcon>
      <UploadIcon></UploadIcon>
      <SendIcon @click="emits('send-message')"></SendIcon>
    </div>
    <Transition name="fade">
      <EmojiPicker
        v-if="pickerOpen"
        ref="pickerWrapper"
        :native="true"
        :hide-search="true"
        :hide-group-names="true"
        :disable-sticky-group-names="true"
        @select="onSelectEmoji"
        class="absolute -top-2 -translate-y-full right-0 bg-main emoji-picker"
      />
    </Transition>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, useModel } from 'vue'
import EmojiPicker from 'vue3-emoji-picker'
import { SmileIcon, UploadIcon, SendIcon } from '@/shared/ui/icons/inputIcons'

import 'vue3-emoji-picker/css'

const modelValue = defineModel()

const emits = defineEmits(['send-message'])

const pickerOpen = ref(false)
const pickerWrapper = ref()

const onSelectEmoji = (emoji: any) => {
  modelValue.value += emoji.i
}

const togglePicker = () => {
  pickerOpen.value = !pickerOpen.value
}
</script>

<style></style>
