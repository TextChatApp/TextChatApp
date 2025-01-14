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

  const getRoomInfo = async (roomId: any, serverId: any) => {
    const token = localStorage.getItem('token')
    try {
      const [{ data: roomData }, { data: membersData }] = await Promise.all([
        roomInfo(roomId, token),
        serverUsers(serverId)
      ])
      infoRoom.value = roomData
      membersServer.value = membersData
    } catch (err) {
      console.log(err)
    }
  }

  return {
    servers,
    infoRoom,
    membersServer,
    getMyServers,
    getRoomInfo
  }
})
