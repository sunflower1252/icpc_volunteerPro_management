import type { AxiosProgressEvent,GenericAbortSignal, AxiosRequestConfig } from 'axios'
import request from './axios'

/**
 * @interface HttpOption
 * @description HTTP 请求的选项
 * @property {string} url - 请求的 URL
 * @property {any} [data] - 请求的数据
 * @property {string} [method] - 请求的方法，如 'GET'、'POST' 等
 * @property {any} [headers] - 请求的头部信息
 * @property {(progressEvent: AxiosProgressEvent) => void} [onDownloadProgress] - 下载进度的回调函数
 * @property {GenericAbortSignal} [signal] - 用于取消请求的信号
 * @property {() => void} [beforeRequest] - 在请求发送之前调用的函数
 * @property {() => void} [afterRequest] - 在请求发送之后调用的函数
 */
export interface HttpOption {
    url: string
    data?: any
    method?: string
    headers?: any
    onDownloadProgress?: (progressEvent: AxiosProgressEvent) => void
    signal?: GenericAbortSignal
    beforeRequest?: () => void
    afterRequest?: () => void
}

/**
 * 与后端匹配
 * @description 返回参数
 * @template T
 */
export interface Response<T = any> {
    data: T
    msg: string
    code: number
}

/**
 * @async
 * @function get
 * @template T
 * @description 发送 GET 请求
 * @param {HttpOption} options - HTTP 请求的选项
 * @returns {Promise<Response<T>>} 返回一个 Promise，它在解析时返回响应的数据
 * @throws {Error} 如果请求失败，会抛出一个错误
 */
export async function get<T = any>(options: HttpOption): Promise<Response<T>> {
    const config: AxiosRequestConfig = {
        method: 'GET',
        url: options.url,
        headers: options.headers,
        onDownloadProgress: options.onDownloadProgress,
        signal: options.signal,
    };

    if (options.beforeRequest) options.beforeRequest();
    const response: Response<T> = await request(config);
    if (options.afterRequest) options.afterRequest();

    return response;
}


/**
 * @async
 * @function post
 * @template T
 * @description 发送 POST 请求
 * @param {HttpOption} options - HTTP 请求的选项
 * @returns {Promise<Response<T>>} 返回一个 Promise，它在解析时返回响应的数据
 * @throws {Error} 如果请求失败，会抛出一个错误
 */
export async function post<T = any>(options: HttpOption): Promise<Response<T>> {
    const config: AxiosRequestConfig = {
        method: 'POST',
        url: options.url,
        data: options.data,
        headers: options.headers,
        onDownloadProgress: options.onDownloadProgress,
        signal: options.signal,
    };

    if (options.beforeRequest) options.beforeRequest();
    const response: Response<T> = await request(config);
    if (options.afterRequest) options.afterRequest();

    return response;
}