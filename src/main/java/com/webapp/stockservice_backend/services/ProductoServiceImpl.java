package com.webapp.stockservice_backend.services;

import com.webapp.stockservice_backend.models.Producto;
import com.webapp.stockservice_backend.repositories.ProductoRepository;
import com.webapp.stockservice_backend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired 
    private ProductoRepository productoRepository;

    @Override 
    public Producto registrarProducto(Producto producto) throws Exception {
        try {
            return productoRepository.save(producto);
        } catch (Exception e) {
            throw new Exception("Error al registrar el producto", e);
        }
    }

    @Override
    public Producto actualizarStock(Long id, int cantidad) throws Exception { 
        try {
            Optional<Producto> optionalProducto = productoRepository.findById(id);
            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();
                producto.setCantidad(cantidad);
                return productoRepository.save(producto);
            } else {
                throw new Exception("Producto no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizar el stock del producto", e);
        }
    }

    @Override
    public List<Producto> obtenerProductosObsoletos() {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getCantidad() < 10)
                .toList();
    }

    @Override
    public List<Producto> filtrarProductosPorTipo(String tipo) {
        return productoRepository.findAll().stream()
                .filter(producto -> producto.getClass().getSimpleName().equalsIgnoreCase(tipo))
                .toList();
    }

    @Override
    public List<Producto> filtrarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }
}
