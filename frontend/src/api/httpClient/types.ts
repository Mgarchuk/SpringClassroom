export interface IHttpClient {
  connect: (nickname: string) => Promise<IConnectResponse>
}

export interface IConnectResponse {
  connectStatus: boolean
}
