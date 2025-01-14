import { http } from '@/shared/api'

const token = localStorage.getItem('token')

export const serverCreate = (token: string | null, data: object) => {
  return http.post(`/servers/create`, data, {
    headers: {
      Authorization: token
    }
  })
}

export const myServersQuery = (token: string | null) => {
  return http.get(`/servers/my-servers`, {
    headers: {
      Authorization: token
    }
  })
}

export const roomInfo = (id: any, token: string | null) => {
  return http.get(`/rooms/${id}`, {
    headers: {
      Authorization: token
    }
  })
}

export const serverInfoQuery = (id: any, token: string | null) => {
  return http.get(`/servers/${id}`, {
    headers: {
      Authorization: token
    }
  })
}

export const joinServerQuery = (inviteCode: any, token: string | null) => {
  return http.post(
    `/servers/join`,
    {
      inviteCode: inviteCode
    },
    {
      headers: {
        Authorization: token
      }
    }
  )
}

export const serverUsers = (serverId: any) => {
  return http.get(`/servers/${serverId}/users`, {
    headers: {
      Authorization: token
    }
  })
}
