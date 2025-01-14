import { http } from '@/shared/api'

const token = localStorage.getItem('token')

export const serverCreate = (token: string | null, data: object) => {
  return http.post(`/servers/create`, data, {
    headers: {
      Authorization: token
    }
  })
}

export const roomCreate = (serverId: any, data: object) => {
  return http.post(`/servers/${serverId}/rooms`, data, {
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

export const deleteUserQuery = (serverId: any, userId: any) => {
  return http.delete(`/servers/${serverId}/users/${userId}`, {
    headers: {
      Authorization: token
    }
  })
}

export const updateUserQuery = (serverId: any, userId: any, newRole: string) => {
  return http.patch(
    `/servers/${serverId}/users/${userId}/role`,
    {
      role: newRole
    },
    {
      headers: {
        Authorization: token
      }
    }
  )
}

export const leaveFromServerQuery = (serverId: any) => {
  return http.delete(`/servers/${serverId}/leave`, {
    headers: {
      Authorization: token
    }
  })
}

export const updateRoomNameQuery = (roomId: any, newName: string) => {
  return http.patch(
    `/servers/rooms/${roomId}`,
    {
      name: newName
    },
    {
      headers: {
        Authorization: token
      }
    }
  )
}

export const deleteRoomQuery = (roomId: any) => {
  return http.delete(`/servers/rooms/${roomId}`, {
    headers: {
      Authorization: token
    }
  })
}
