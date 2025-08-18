// src/router/index.js
import AppLayout from '@/layout/AppLayout.vue';
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        component: AppLayout,
        meta: { requiresAuth: true }, // ← Koruma için işaret
        children: [
            {
                path: '/',
                name: 'dashboard',
                component: () => import('@/views/Dashboard.vue')
            },
            {
                path: 'uikit/formlayout',
                name: 'formlayout',
                component: () => import('@/views/uikit/FormLayout.vue')
            },
            {
                path: 'uikit/input',
                name: 'input',
                component: () => import('@/views/uikit/InputDoc.vue')
            },
            {
                path: 'uikit/button',
                name: 'button',
                component: () => import('@/views/uikit/ButtonDoc.vue')
            },
            {
                path: 'uikit/table',
                name: 'table',
                component: () => import('@/views/uikit/TableDoc.vue')
            },
            {
                path: 'uikit/list',
                name: 'list',
                component: () => import('@/views/uikit/ListDoc.vue')
            },
            {
                path: 'uikit/tree',
                name: 'tree',
                component: () => import('@/views/uikit/TreeDoc.vue')
            },
            {
                path: 'uikit/panel',
                name: 'panel',
                component: () => import('@/views/uikit/PanelsDoc.vue')
            },
            {
                path: 'uikit/overlay',
                name: 'overlay',
                component: () => import('@/views/uikit/OverlayDoc.vue')
            },
            {
                path: 'uikit/media',
                name: 'media',
                component: () => import('@/views/uikit/MediaDoc.vue')
            },
            {
                path: 'uikit/message',
                name: 'message',
                component: () => import('@/views/uikit/MessagesDoc.vue')
            },
            {
                path: 'uikit/file',
                name: 'file',
                component: () => import('@/views/uikit/FileDoc.vue')
            },
            {
                path: 'uikit/menu',
                name: 'menu',
                component: () => import('@/views/uikit/MenuDoc.vue')
            },
            {
                path: 'uikit/charts',
                name: 'charts',
                component: () => import('@/views/uikit/ChartDoc.vue')
            },
            {
                path: 'uikit/misc',
                name: 'misc',
                component: () => import('@/views/uikit/MiscDoc.vue')
            },
            {
                path: 'uikit/timeline',
                name: 'timeline',
                component: () => import('@/views/uikit/TimelineDoc.vue')
            },
            {
                path: 'views/city-crud',
                name: 'cityCrud',
                component: () => import('@/views/CityCrud.vue')
            },
            {
                path: 'views/port-crud',
                name: 'portCrud',
                component: () => import('@/views/PortCrud.vue')
            },
            {
                path: 'views/exchange-rate-crud',
                name: 'exchangeRateCrud',
                component: () => import('@/views/ExchangeRateCrud.vue')
            },
            {
                path: 'views/port-chart',
                name: 'PortChart',
                component: () => import('@/views/PortChart.vue')
            },
            {
                path: '/exchange-rate-chart1',
                name: 'ExchangeRateChart1',
                component: () => import('@/views/ExchangeRateChart1.vue')
            }

        ]
    },

    {
        path: '/auth/login',
        name: 'login',
        component: () => import('@/views/pages/auth/Login.vue')
    },

];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// Global guard
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('auth_token');

    // 1) Auth gerektiren sayfalara izinsiz giriş engeli
    if (to.matched.some((r) => r.meta.requiresAuth) && !token) {
        return next({ name: 'login' });
    }

    // 2) Girişli kullanıcıyı tekrar login/register sayfasına engelle. Kullanıcı login olduysa, login/error sayfalarına tekrar gidemesin.
    if ((to.name === 'login' || to.name === 'accessDenied' || to.name === 'error') && token) {
        return next({ name: 'dashboard' });
    }

    // 3) Diğer durumlarda devam et
    next();
});

export default router;



