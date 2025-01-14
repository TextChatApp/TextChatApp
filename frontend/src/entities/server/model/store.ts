import { defineStore } from 'pinia'
import { ref, computed, reactive } from 'vue'
import { myServersQuery, serverUsers, roomInfo } from '../api'

export const useServerStore = defineStore('serverStore', () => {
  const servers = ref()
  const infoRoom = ref()
  const membersServer = ref()

  const getMyServers = async () => {
    const token = localStorage.getItem('token')
    try {
      const { data } = await myServersQuery(token)
      servers.value = data
    } catch (err: any) {
      console.log(err)
    }
  }

  const setNewServer = (server: any) => {
    servers.value.push(server)
  }

  const setNewRoleMember = (id: any, newRole: string) => {
    if (membersServer.value) {
      const updatedUser = membersServer.value.find((item: any) => item.id == id)
      console.log(updatedUser)
      updatedUser.role = newRole
    }
  }

  const deleteMemberFromServer = (id: any) => {
    if (membersServer.value) {
      const deletedUserIndex = membersServer.value.findIndex((item: any) => item.id == id)
      membersServer.value.splice(deletedUserIndex, 1)
    }
  }

  const getRoomInfo = async (roomId: any, serverId: any) => {
    const token = localStorage.getItem('token')
    try {
      const [{ data: roomData }, { data: membersData }] = await Promise.all([
        roomInfo(roomId, token),
        serverUsers(serverId)
      ])
      infoRoom.value = roomData
      membersServer.value = membersData
    } catch (err) {}
  }

  return {
    servers,
    infoRoom,
    membersServer,
    getMyServers,
    getRoomInfo,
    setNewServer,
    setNewRoleMember,
    deleteMemberFromServer
  }
})
