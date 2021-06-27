import { Input as ChakraInput, Box, Text, InputProps } from '@chakra-ui/react'
import React from 'react'

interface Props extends Omit<InputProps, 'onChange'> {
  error?: string
  onChange?: (value?: string) => void
}

export const Input: React.FC<Props> = ({ error, onChange, ...props }) => {
  return (
    <Box width="100%" {...props}>
      <ChakraInput width="100%" {...props} onChange={(event) => onChange?.(event?.target?.value)} />
      <Box height="20px">
        {error && (
          <Text color="red" fontSize="sm">
            {error}
          </Text>
        )}
      </Box>
    </Box>
  )
}
