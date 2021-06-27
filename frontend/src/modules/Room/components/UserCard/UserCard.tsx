import { Flex, FlexProps, Text, Image } from '@chakra-ui/react'
import React from 'react'

import { IUser, UserEmotion } from '../../../../types'
import { images } from '../../images'

interface Props extends FlexProps {
  user: IUser
}

export const UserCard: React.FC<Props> = ({ user, ...props }) => {
  return (
    <Flex
      {...props}
      borderRadius="10px"
      boxShadow="0 0 20px rgba(0, 0, 0, 0.1)"
      paddingX="40px"
      paddingY="20px"
      position="relative"
    >
      <Text>{user.nickname}</Text>
      {user.state === UserEmotion.handUp && (
        <Image
          height="40px"
          position="absolute"
          right="-20px"
          src={images.handUp}
          top="-20px"
          width="40px"
        />
      )}
      {user.state === UserEmotion.handDown && (
        <Image
          height="40px"
          position="absolute"
          right="-20px"
          src={images.handDown}
          top="-20px"
          width="40px"
        />
      )}
    </Flex>
  )
}
