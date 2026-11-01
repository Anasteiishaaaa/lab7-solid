using System.Collections.Generic;

namespace Isp
{
    // Role-based interfaces: кожен описує ОДНУ роль, яку клієнт реально використовує.

    /// <summary>Роль: читання даних.</summary>
    public interface IReadRepository<T>
    {
        List<T> GetAll();
        T GetById(int id);
    }

    /// <summary>Роль: зміна даних.</summary>
    public interface IWriteRepository<T>
    {
        void Add(T entity);
        void Update(T entity);
        void Delete(int id);
    }

    /// <summary>Роль: експорт у CSV. Не залежить від типу сутності, тому не generic.</summary>
    public interface ICsvExporter
    {
        void ExportToCsv(string path);
    }

    /// <summary>Роль: повне очищення сховища.</summary>
    public interface IPurgeable
    {
        void PurgeAll();
    }
}
