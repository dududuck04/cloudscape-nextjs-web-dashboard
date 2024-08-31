import React from 'react';
import '@cloudscape-design/global-styles/index.css';
import type { AppProps } from 'next/app';

export function MyApp({ Component, pageProps }: AppProps) {
    return (
        <Component {...pageProps} />
    );
}