import { get, post } from '../utils/request/index'

export function getTest() {
  return get({
    url: '/tbUser/hello',
  })
}