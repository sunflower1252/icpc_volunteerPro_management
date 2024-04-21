import axios from 'axios';

/**
 * @author :lse
 * @date :2024-2-21
 * @description :Axios的配置
 */
// 创建axios实例
const service = axios.create({
    // 请求的基础路径获取的是env里面的参数
    baseURL: import.meta.env.VITE_APP_BASE_URL,
    // 请求超时时间
    timeout: 5000,
    // 请求头信息
});

// 请求拦截器
service.interceptors.request.use(config => {
    // 在发送请求之前做些什么
    return config;
}, error => {
    console.error('Request error:', error);
});

// 响应拦截器
service.interceptors.response.use(response => {
    return response.data;
}, error => {

    return Promise.reject(error);
});

export default service;


