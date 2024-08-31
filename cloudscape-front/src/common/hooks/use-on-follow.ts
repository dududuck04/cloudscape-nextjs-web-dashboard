import { useCallback } from 'react';
import { useRouter } from 'next/router';

interface FollowDetail {
    external?: boolean;
    href?: string;
}

export function useOnFollow() {
    const router = useRouter();

    return useCallback(
        (event: CustomEvent<FollowDetail>): void => {
            if (
                event.detail.external === true ||
                typeof event.detail.href === "undefined"
            ) {
                return;
            }

            event.preventDefault();
            router.push(event.detail.href);
        },
        [router],
    );
}
