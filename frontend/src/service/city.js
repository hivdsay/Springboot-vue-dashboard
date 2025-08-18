import api from '@/api'; // Eğer api.js dosyan varsa axios buradan yönetilmeli

export const getAllCities = async () => {
  const response = await api.get('/cities')
  return response.data
};

export const createCity = async (data) => {
  return await api.post('/cities', data)
};

export const updateCity = async (data) => {
  return await api.put(`/cities/${data.city}`, data)
};

export const deleteCityById = async (id) => {
  return await api.delete(`/cities/${id}`)
};

export const getTotalCityCount = async () => {
    const response = await api.get('/cities/count');
    return response.data;
};
