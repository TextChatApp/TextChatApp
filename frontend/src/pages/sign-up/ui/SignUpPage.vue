<template>
  <div class="h-screen flex justify-center items-center">
    <div class="container px-8 flex justify-center items-center">
      <div class="px-8 py-10 bg-white text-main rounded-3xl shadow-2xl shadow-white w-80 sm:w-96">
        <div class="flex flex-col justify-center items-center gap-7">
          <h2 class="font-bold text-3xl">Welcome</h2>
          <Logo></Logo>
          <div class="w-full">
            <RegisterForm class="mb-2"></RegisterForm>
          </div>
          <p>
            Already have an account?
            <router-link class="text-accent-main" to="/login">Sign in</router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Logo } from '@/shared/ui/logo'
import { RegisterForm } from '@/features/auth'
</script>

<style></style>
