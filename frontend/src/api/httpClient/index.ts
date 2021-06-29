import axios from 'axios'

import { config } from '../../config'

import { IHttpClient } from './types'

export const createHttpClient = (): IHttpClient => {
  const client = axios.create({ baseURL: config.serverURL })
  return {
    connect: async (username: string) => {
      try {
        const { data } = await client.put('/users/add', { username })
        return JSON.parse(data)
      } catch {
        return { connectStatus: true }
      }
    },
  }
}
