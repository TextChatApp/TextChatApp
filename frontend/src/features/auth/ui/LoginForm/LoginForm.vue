<template>
  <div class="flex flex-col gap-8 items-center">
    <LoginInput class="w-full" v-model="username">
      <img :src="humanIcon" alt="humonIcon" />
    </LoginInput>
    <LoginInput class="w-full" :inputType="'password'" v-model="password">
      <img :src="passwordIcon" alt="humonIcon" />
    </LoginInput>
    <ButtonLogin class="text-center" :text="'Login'"></ButtonLogin>
  </div>
</template>

<script setup lang="ts">
import humanIcon from '@/shared/assets/image/human-icon.png'
import passwordIcon from '@/shared/assets/image/password-icon.png'
import ButtonLogin from '@/shared/ui/buttons/ButtonLogin'
import LoginInput from '@/shared/ui/inputs/LoginInput'
import { ref } from 'vue'

const username = ref('')
const password = ref('')
</script>

<style></style>
