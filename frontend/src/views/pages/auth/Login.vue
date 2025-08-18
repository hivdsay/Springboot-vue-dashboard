<script setup>
import FloatingConfigurator from '@/components/FloatingConfigurator.vue'
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Checkbox from 'primevue/checkbox'
import Button from 'primevue/button'
import { ref } from 'vue'
import { useAuth } from '@/composables/useAuth'

const { login, isLoading, error } = useAuth()

const email = ref('')
const password = ref('')
const checked = ref(false)

function onSubmit() {
  login(email.value, password.value)
}
</script>

<template>
  <FloatingConfigurator />
  <div class="bg-surface-50 dark:bg-surface-950 flex items-center justify-center min-h-screen min-w-[100vw] overflow-hidden">
    <div class="flex flex-col items-center justify-center">
      <div
        style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)"
      >
        <div class="w-full bg-surface-0 dark:bg-surface-900 py-20 px-8 sm:px-20" style="border-radius: 53px">
          <form @submit.prevent="onSubmit">
            <label for="email" class="block text-xl font-medium mb-2">Email</label>
            <InputText
              id="email"
              v-model="email"
              placeholder="Email address"
              class="w-full md:w-[30rem] mb-8"
            />

            <label for="password" class="block text-xl font-medium mb-2">Password</label>
            <Password
              id="password"
              v-model="password"
              placeholder="Password"
              :toggleMask="true"
              :feedback="false"
              class="mb-4"
            />

            <div class="flex items-center justify-between mt-2 mb-8 gap-8">
              <div class="flex items-center">
                <Checkbox v-model="checked" id="rememberme1" binary class="mr-2" />
                <label for="rememberme1">Remember me</label>
              </div>
              <span class="font-medium cursor-pointer text-primary">Forgot password?</span>
            </div>

            <Button
              label="Sign In"
              class="w-full"
              :loading="isLoading"
              :disabled="isLoading"
              type="submit"
            />

            <p v-if="error" class="text-red-600 mt-4 text-center">{{ error }}</p>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.pi-eye,
.pi-eye-slash {
  transform: scale(1.6);
  margin-right: 1rem;
}
</style>



