import React, { useContext, useEffect, useRef, useState } from 'react'
import StompClient from "react-stomp-client";

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

const SOCKET_URL = 'ws://localhost:8080/broadcast'

export const ConnectProvider: React.FC = ({ children }) => {
  const [ownState, updateOwnState] = useState<IUser | null>(null)

  const [users, setUsers] = useState<IUser[]>([])

  const SOCKET_EVENTS = {
    USER_ACTION: 'user-action'
  }

  return (
    <StompClient endpoint={SOCKET_URL} onMessage={console.log}>
      <ConnectProviderContext.Provider value={{ ownState, updateOwnState, users }}>
        {children}
      </ConnectProviderContext.Provider>
    </StompClient>
  )
}

