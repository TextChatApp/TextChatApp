<template>
  <div class="h-screen flex justify-center items-center bg-black">
    <div class="container px-8 flex justify-center items-center">
      <div
        class="relative px-5 py-10 bg-main text-main rounded-3xl shadow-2xl shadow-input-bg w-80 sm:w-96"
      >
        <button
          class="absolute top-3 right-5 text-white text-xl hover:scale-150 transition-all"
          @click="back()"
        >
          x
        </button>
        <div class="flex flex-col justify-center gap-7 text-white">
          <h2 class="font-bold text-2xl mb-7">My account</h2>
          <div class="flex justify-between items-end flex-wrap gap-y-3">
            <div class="profile flex items-end gap-3">
              <Avatar :url="userInfo?.avatar" :size="50"></Avatar>
              <span class="text-xl font-bold">{{ userInfo?.username }}</span>
            </div>
            <div>
              <ChangeAvatar></ChangeAvatar>
            </div>
          </div>
          <div class="change-form flex flex-col gap-5 bg-input-bg p-3 rounded-lg">
            <div class="flex justify-between items-end flex-wrap">
              <div class="flex flex-col gap-1">
                <span class="text-xs text-grey-main">Your username</span>
                <span>{{ userInfo?.username }}</span>
              </div>
              <button class="rounded-md bg-white text-black px-4 py-1" @click="openPopupUsername()">
                Change
              </button>
            </div>
            <div class="flex justify-between items-end flex-wrap">
              <div class="flex flex-col gap-1">
                <span class="text-xs text-grey-main">Your email</span>
                <span>{{ userInfo?.email }}</span>
              </div>
              <button class="rounded-md bg-white text-black px-4 py-1" @click="openPopupEmail()">
                Change
              </button>
            </div>
          </div>
          <div>
            <h2 class="text-xl mb-3">Password</h2>
            <button class="rounded-md bg-white text-black px-4 py-1" @click="openPopupPassword()">
              Change password
            </button>
          </div>
        </div>
      </div>
    </div>
    <Popup ref="changeUsernameRef">
      <template v-slot:header><h3 class="text-2xl mb-8">Change username</h3></template>
      <template v-slot:default
        ><ChangeUsername @close-popup="closePopupUsername"></ChangeUsername
      ></template>
    </Popup>
    <Popup ref="changeEmailRef">
      <template v-slot:header><h3 class="text-2xl mb-8">Change email</h3></template>
      <template v-slot:default><ChangeEmail @close-popup="closePopupEmail"></ChangeEmail></template>
    </Popup>
    <Popup ref="changePasswordRef">
      <template v-slot:header><h3 class="text-2xl mb-8">Change password</h3></template>
      <template v-slot:default
        ><ChangePassword @close-popup="closePopupPassword"></ChangePassword
      ></template>
    </Popup>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getMeInfo } from '@/entities/user'
import { Logo } from '@/shared/ui/logo'
import { useUser } from '@/entities/user'
import Avatar from '@/shared/ui/avatar'
import { LoginForm } from '@/features/auth'
import user from '@/shared/assets/image/default-avatar-accent.png'
import { storeToRefs } from 'pinia'
import Popup from '@/shared/ui/popup'
import { useRouter } from 'vue-router'

import { ChangeUsername, ChangeEmail, ChangePassword } from '@/features/user/change-username'
import ChangeAvatar from '@/features/user/change-avatar'

const userStore = useUser()
const { userInfo } = storeToRefs(userStore)

const router = useRouter()

const changeUsernameRef = ref()
const changeEmailRef = ref()
const changePasswordRef = ref()

const openPopupUsername = () => {
  changeUsernameRef.value?.open()
}

const closePopupUsername = () => {
  changeUsernameRef.value?.close()
}

const openPopupEmail = () => {
  changeEmailRef.value?.open()
}

const closePopupEmail = () => {
  changeEmailRef.value?.close()
}

const openPopupPassword = () => {
  changePasswordRef.value?.open()
}

const closePopupPassword = () => {
  changePasswordRef.value?.close()
}

const back = () => {
  router.go(-1)
}

onMounted(async () => {
  console.log(userInfo.value)
})
</script>

<style></style>
