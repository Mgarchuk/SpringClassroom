import React from 'react'
import { BrowserRouter, Redirect, Route, Switch } from 'react-router-dom'

import { Providers } from './lib'
import { Login, Room } from './modules'

export const App = () => {
  return (
    <Providers>
      <BrowserRouter>
        <Switch>
          <Route component={Room} path="/room" />
          <Route component={Login} path="/" />
          <Redirect to="/" />
        </Switch>
      </BrowserRouter>
    </Providers>
  )
}
