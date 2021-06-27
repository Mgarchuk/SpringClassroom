import { Box, Button, Flex, Text } from '@chakra-ui/react'
import React from 'react'
import { useHistory } from 'react-router-dom'
import { object, string } from 'yup'

import { createHttpClient } from '../../api'
import { Input } from '../../components'
import { useConnect, useForm } from '../../lib'
import { UserEmotion } from '../../types'

const initialValues = {
  nickname: '',
}

const validationSchema = object({
  nickname: string().required('This field is required'),
}).required()

export const Login: React.FC = () => {
  const history = useHistory()

  const { updateOwnState } = useConnect()

  const onSubmit = async ({ nickname }: { nickname?: string }) => {
    if (!nickname) return
    const client = createHttpClient()
    const { connectStatus } = await client.connect(nickname)
    if (connectStatus) {
      updateOwnState({ nickname: nickname, state: UserEmotion.inAction })
      history.push('/room')
    }
  }

  const { field, submitProps } = useForm({ initialValues, validationSchema, onSubmit })

  return (
    <Flex alignItems="center" flexDirection="column" height="100vh" justifyContent="center">
      <Text fontSize="3xl" fontWeight="bold" mb="20px">
        Start communicate now!
      </Text>
      <Input placeholder="Your nickname" width="400px" {...field('nickname')} />
      <Box mt="20px" {...submitProps}>
        <Button width="400px">Connect</Button>
      </Box>
    </Flex>
  )
}
