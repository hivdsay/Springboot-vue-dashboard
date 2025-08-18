import api from '@/api.js'

export async function login({ email, password }) {
  const { data } = await api.post('/auth/login', { email, password })
  return data.token
}

export async function register({ email, password }) {
  await api.post('/auth/register', { email, password })
}

export function logout() {
  localStorage.removeItem('auth_token')
}
