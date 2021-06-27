import { Button, Flex, Image } from '@chakra-ui/react'
import React from 'react'
import { Redirect } from 'react-router-dom'

import { useConnect } from '../../lib'
import { UserEmotion } from '../../types'

import { UserCard } from './components'
import { images } from './images'

export const Room: React.FC = () => {
  const { users, ownState, updateOwnState } = useConnect()

  if (!ownState) {
    return <Redirect to="/" />
  }

  const onLogout = () => {
    updateOwnState(null)
  }

  const onHandUp = () => {
    updateOwnState({ ...ownState, state: UserEmotion.handUp })
  }

  const onHandDown = () => {
    updateOwnState({ ...ownState, state: UserEmotion.handDown })
  }

  return (
    <Flex
      flexWrap="wrap"
      justifyContent="center"
      margin="0 auto"
      maxWidth="1000px"
      mt="100px"
      width="100vw"
    >
      {users?.map((user) => (
        <UserCard key={user.nickname} mb="30px" mr="30px" user={user} />
      ))}
      <Flex justifyContent="space-evenly" mt="40px" width="100%">
        <Button height="60px" padding="20px" onClick={onHandUp}>
          <Image height="40px" src={images.handUp} />
        </Button>
        <Button height="60px" padding="20px">
          <Image height="40px" src={images.handDown} onClick={onHandDown} />
        </Button>
        <Button height="60px" padding="20px" onClick={onLogout}>
          Logout
        </Button>
      </Flex>
    </Flex>
  )
}
