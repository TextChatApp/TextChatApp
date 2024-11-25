<template>
  <div class="overflow-y-auto md:w-72">
    <h2 class="text-2xl sm:text-3xl font-bold mb-6">Messages</h2>
    <div class="mb-10 w-full"><ChatsInput></ChatsInput></div>
    <div class="w-full">
      <div class="flex items-center gap-2 mb-4">
        <img :src="chatsIcon" alt="" />
        <h3>Personal chats</h3>
      </div>
      <div class="flex flex-col gap-7 items-center justify-center w-full mb-3">
        <!-- cards -->
        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>
      </div>
      <div class="flex flex-col gap-7 items-center justify-center w-full mb-3">
        <!-- cards -->
        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>
      </div>
      <div class="flex flex-col gap-7 items-center justify-center w-full mb-3">
        <!-- cards -->
        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>

        <div class="flex items-center justify-between w-full">
          <div class="flex gap-x-4">
            <div>
              <img :src="user" alt="" width="50" height="50" />
            </div>
            <div>
              <span class="block font-bold text-base">Design Team</span>
              <p class="block text-sm">Hello! Everyone</p>
            </div>
          </div>

          <div>
            <span class="block font-bold text-xs text-grey-main">4:30 PM</span
            ><span class="block font-bold text-xs text-grey-main">dsdsd</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ChatsInput from '@/shared/ui/inputs/ChatsInput'
import chatsIcon from '@/shared/assets/image/chats.png'
import user from '@/shared/assets/image/user.png'
</script>

<style></style>
