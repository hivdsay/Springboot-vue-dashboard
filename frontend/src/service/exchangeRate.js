import api from '@/api';

export const getAllExchangeRates = async () => {
  const response = await api.get('/exchange-rates')
  return response.data
};

export const createExchangeRate = async (data) => {
  return await api.post('/exchange-rates', data)
};

export const updateExchangeRate = async (data) => {
  return await api.put(`/exchange-rates/${data.id}`, data)
};

export const deleteExchangeRateById = async (id) => {
  return await api.delete(`/exchange-rates/${id}`)
};
export const getTotalExchangeRateCount = async () => {
    const response = await api.get('/exchange-rates/count');
    return response.data;
};
