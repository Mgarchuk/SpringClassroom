import { ChakraProvider } from '@chakra-ui/react'
import React from 'react'

import { ConnectProvider } from './ConnectProvider'

export { useConnect } from './ConnectProvider'

export const Providers: React.FC = ({ children }) => {
  return (
    <ChakraProvider>
      <ConnectProvider>{children}</ConnectProvider>
    </ChakraProvider>
  )
}
