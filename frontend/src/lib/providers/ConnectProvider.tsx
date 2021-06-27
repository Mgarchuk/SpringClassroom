import React, { useContext, useEffect, useRef, useState } from 'react'
import { io, Socket } from 'socket.io-client'

import { config } from '../../config'
import { IUser } from '../../types'

interface IConnectProviderContext {
  users: IUser[]
  ownState: IUser | null
  updateOwnState: (state: IUser | null) => void
}

const ConnectProviderContext = React.createContext<IConnectProviderContext>(
  {} as IConnectProviderContext,
)

export const useConnect = (): IConnectProviderContext => {
  const context = useContext<IConnectProviderContext>(ConnectProviderContext)
  if (!context) throw new Error('[useConnect] Cannot use this hook outside the ConnectProvider')
  return context
}

export const ConnectProvider: React.FC = ({ children }) => {
  const [ownState, updateOwnState] = useState<IUser | null>(null)
  const socketRef = useRef<Socket | null>(null)
  const [users, setUsers] = useState<IUser[]>([])

  useEffect(() => {
    if (!ownState) return
    socketRef.current = io(config.serverURL, {
      query: {
        nickname: ownState.nickname,
      },
    })

    socketRef.current.on('users', (users) => {
      setUsers(users)
    })

    return () => {
      socketRef.current?.disconnect()
    }
  }, [ownState])

  useEffect(() => {
    if (!ownState || !socketRef.current) return
    socketRef.current.emit('emotion', { ...ownState })
  }, [ownState, socketRef])

  return (
    <ConnectProviderContext.Provider value={{ ownState, updateOwnState, users }}>
      {children}
    </ConnectProviderContext.Provider>
  )
}
