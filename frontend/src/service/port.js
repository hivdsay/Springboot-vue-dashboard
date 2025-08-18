import api from '@/api'; // Centralized axios instance

// Get all ports
export const getAllPorts = async () => {
  const response = await api.get('/ports');
  return response.data;
};

// Create a new port
export const createPort = async (data) => {
  const response = await api.post('/ports', data);
  return response.data;
};

// Update a port by code
export const updatePort = async (code, data) => {
  const response = await api.put(`/ports/${code}`, data);
  return response.data;
};

// Delete a port by code
export const deletePortByCode = async (code) => {
  return await api.delete(`/ports/${code}`);
};

// Get a port by code
export const getPortByCode = async (code) => {
  const response = await api.get(`/ports/${code}`);
  return response.data;
};

// Filter ports by optional name and/or city
export const filterPorts = async (name, city) => {
  const params = {};
  if (name) params.name = name;
  if (city) params.city = city;

  const response = await api.get('/ports/filter', { params });
  return response.data;
};

// Get city count by port range
export const getCityPortCountByRange = async () => {
  const response = await api.get('/ports/port-distribution');
  return response.data;
};

export const getCityPortSummary = async () => {
  const response = await api.get('/ports/by-city');
  return response.data;
};

// Get total number of ports (for dashboard)
export const getTotalPortCount = async () => {
  const response = await api.get('/ports/count');
  return response.data;
};

// Get top port city (for dashboard)
export const getTopPortCity = async () => {
    const response = await api.get('/ports/most-port-city');
    return response.data;
};



