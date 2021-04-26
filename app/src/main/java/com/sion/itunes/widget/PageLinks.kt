package com.sion.itunes.widget

import retrofit2.Response
import java.util.*

class PageLinks(response: Response<Any>) {
    /**
     * @return first
     */
    var first: String? = null

    /**
     * @return last
     */
    var last: String? = null

    /**
     * @return next
     */
    var next: String? = null

    /**
     * @return prev
     */
    var prev: String? = null

    companion object {
        private const val DELIM_LINKS = "," //$NON-NLS-1$
        private const val DELIM_LINK_PARAM = ";" //$NON-NLS-1$

        private const val META_REL = "rel" //$NON-NLS-1$
        private const val META_LAST = "last" //$NON-NLS-1$
        private const val META_NEXT = "next" //$NON-NLS-1$
        private const val META_FIRST = "first" //$NON-NLS-1$
        private const val META_PREV = "prev" //$NON-NLS-1$

        private const val HEADER_LINK = "Link" //$NON-NLS-1$
        private const val HEADER_NEXT = "X-Next" //$NON-NLS-1$
        private const val HEADER_LAST = "X-Last" //$NON-NLS-1$
    }

    /**
     * Parse links from executed method
     *
     * @param response
     */
    init {
        val linkHeader: String = response.headers()[HEADER_LINK] ?:""
        if (linkHeader.isNotEmpty()) {
            val links = linkHeader.split(DELIM_LINKS).toTypedArray()
            for (link in links) {
                val segments = link.split(DELIM_LINK_PARAM).toTypedArray()
                if (segments.size < 2) continue
                var linkPart = segments[0].trim { it <= ' ' }
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) //$NON-NLS-1$ //$NON-NLS-2$
                    continue
                linkPart = linkPart.substring(1, linkPart.length - 1)
                for (i in 1 until segments.size) {
                    val rel = segments[i].trim { it <= ' ' }.split("=").toTypedArray() //$NON-NLS-1$
                    if (rel.size < 2 || !META_REL.equals(rel[0])) continue
                    var relValue = rel[1]
                    if (relValue.startsWith("\"") && relValue.endsWith("\"")) //$NON-NLS-1$ //$NON-NLS-2$
                        relValue = relValue.substring(1, relValue.length - 1)
                    if (META_FIRST.equals(relValue)) first = linkPart else if (META_LAST.equals(
                            relValue
                        )
                    ) last = linkPart else if (META_NEXT.equals(relValue)) next =
                        linkPart else if (META_PREV.equals(relValue)) prev = linkPart
                }
            }
        } else {
            next = response.headers()[HEADER_NEXT]
            last = response.headers()[HEADER_LAST]
        }
    }
}