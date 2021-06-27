import { FormikConfig, useFormik } from 'formik'
import { useCallback } from 'react'
import { AnySchema } from 'yup'

// eslint-disable-next-line @typescript-eslint/ban-types
interface FormProps<Values extends object>
  extends Omit<FormikConfig<Values>, 'validationSchema' | 'initialValues'> {
  initialValues?: Partial<Values>
  validationSchema: AnySchema<Values>
  additionalValidation?: (values: Values) => Record<string, string | boolean | undefined>
}

export interface FieldProps<Value> {
  value: Value
  error?: string
  onChange: (v?: Value) => void
  onSubmit?: () => void
  onFocus: () => void
  onBlur: () => void
}

export interface SubmitProps {
  disabled: boolean
  isLoading: boolean
  onClick: () => void
  type?: 'submit'
}

// eslint-disable-next-line @typescript-eslint/ban-types
export const useForm = <Values extends object>({ ...props }: FormProps<Values>) => {
  const { initialValues, onSubmit, validationSchema, additionalValidation } = props

  const formik = useFormik({
    validateOnChange: false,
    validateOnBlur: false,
    initialValues: (initialValues || {}) as Values,
    validationSchema,
    onSubmit: async (values, formikHelpers) => {
      const extraErrors = additionalValidation?.(values)

      if (extraErrors && Object.keys(extraErrors).length !== 0) {
        for (const record in extraErrors) {
          // eslint-disable-next-line @typescript-eslint/ban-ts-comment
          // @ts-ignore
          const [key, value] = record
          formikHelpers.setFieldError(key, value)
        }
        return
      }

      const errors = await onSubmit(values, formikHelpers)

      if (errors) {
        for (const field in errors) {
          formikHelpers.setFieldError(field, errors[field])
        }
      }
    },
  })

  const submitProps: SubmitProps = {
    disabled: formik.isSubmitting,
    isLoading: formik.isSubmitting,
    onClick: formik.handleSubmit,
    type: 'submit',
  }

  const field = useCallback(
    <Name extends keyof Values & string>(
      name: Name,
      options?: { allowSubmit?: boolean },
    ): FieldProps<Values[Name]> => {
      return {
        onFocus: () => formik.setFieldError(name, ''),
        onBlur: () => formik.handleBlur(name),
        error: formik.errors[name] as string,
        onChange: (value?: Values[Name]) => {
          void formik.setFieldValue(name, value)
          formik.setFieldError(name, '')
        },
        value: formik.values[name],
        onSubmit: options?.allowSubmit ? formik.submitForm : undefined,
      }
    },
    [formik],
  )

  return {
    formik,
    submitProps,
    field,
  }
}
