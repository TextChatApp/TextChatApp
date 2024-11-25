<template>
  <div class="flex flex-col gap-8 items-center">
    <LoginInput class="w-full" :inputType="'email'" :placeholder="'your email'">
      <img :src="mailIcon" alt="password" />
    </LoginInput>
    <LoginInput class="w-full" :placeholder="'your username'">
      <img :src="humanIcon" alt="humonIcon" />
    </LoginInput>
    <LoginInput class="w-full" :inputType="'password'" :placeholder="'your password'">
      <img :src="passwordIcon" alt="password" />
    </LoginInput>
    <ButtonLogin class="text-center" :text="'Register'"></ButtonLogin>
  </div>
</template>

<script setup lang="ts">
import humanIcon from '@/shared/assets/image/human-icon.png'
import passwordIcon from '@/shared/assets/image/password-icon.png'
import mailIcon from '@/shared/assets/image/mail-icon.png'
import ButtonLogin from '@/shared/ui/buttons/ButtonLogin'
import LoginInput from '@/shared/ui/inputs/LoginInput'
</script>

<style></style>
