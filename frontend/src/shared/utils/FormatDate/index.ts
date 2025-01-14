import { DateTime, Duration, Info, Interval, Settings } from 'luxon'

export const formatDate = (isoDate: any) => {
  const date = DateTime.fromISO(isoDate)
  const formattedDate = date.toFormat('HH:mm')
  return formattedDate
}
