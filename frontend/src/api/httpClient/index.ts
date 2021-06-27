import axios from 'axios'

import { config } from '../../config'

import { IConnectResponse, IHttpClient } from './types'

export const createHttpClient = (): IHttpClient => {
  const client = axios.create({ baseURL: config.serverURL })
  return {
    connect: async (nickname: string) => {
      try {
        const { data } = await client.put<IConnectResponse>('/connect', { nickname })
        return data
      } catch {
        return { connectStatus: true }
      }
    },
  }
}
