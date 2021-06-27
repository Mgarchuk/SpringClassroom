export enum UserEmotion {
  handUp,
  handDown,
  inAction,
}

export interface IUser {
  nickname: string
  state: UserEmotion
}
